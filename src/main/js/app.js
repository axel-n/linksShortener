'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const when = require('when');
const client = require('./client');

const follow = require('./follow'); // function to hop multiple links by "rel"

const stompClient = require('./websocket-listener');

const root = '/api';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {links: [], attributes: [], page: 1, pageSize: 2, _links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onUpdate = this.onUpdate.bind(this);
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
		this.refreshCurrentPage = this.refreshCurrentPage.bind(this);
		this.refreshAndGoToLastPage = this.refreshAndGoToLastPage.bind(this);
	}

	loadFromServer(pageSize) {
		follow(client, root, [
				{rel: 'links', params: {size: pageSize}}]
		).then(linkCollection => {
				return client({
					method: 'GET',
					path: linkCollection.entity._links.profile.href,
					headers: {'Accept': 'application/schema+json'}
				}).then(schema => {
					this.schema = schema.entity;
					this._links = linkCollection.entity._links;
					return linkCollection;
				});
		}).then(linkCollection => {
			this.page = linkCollection.entity.page;
			return linkCollection.entity._embedded.links.map(link =>
					client({
						method: 'GET',
						path: link._links.self.href
					})
			);
		}).then(linkPromises => {
			return when.all(linkPromises);
		}).done(links => {
			this.setState({
				page: this.page,
				links: links,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				_links: this._links
			});
		});
	}

	// tag::on-create[]
	onCreate(newLink) {
		follow(client, root, ['links']).done(response => {
			client({
				method: 'POST',
				path: response.entity._links.self.href,
				entity: newLink,
				headers: {'Content-Type': 'application/json'}
			})
		})
	}
	// end::on-create[]

	onUpdate(link, updatedLink) {
		client({
			method: 'PUT',
			path: link.entity._links.self.href,
			entity: updatedLink,
			headers: {
				'Content-Type': 'application/json',
				'If-Match': link.headers.Etag
			}
		}).done(response => {
			/* Let the websocket handler update the state */
		}, response => {
			if (response.status.code === 412) {
				alert('DENIED: Unable to update ' + link.entity._links.self.href + '. Your copy is stale.');
			}
		});
	}

	onDelete(link) {
		client({method: 'DELETE', path: link.entity._links.self.href});
	}

	onNavigate(navUri) {
		client({
			method: 'GET',
			path: navUri
		}).then(linkCollection => {
			this._links = linkCollection.entity._links;
			this.page = linkCollection.entity.page;

			return linkCollection.entity._embedded.links.map(link =>
					client({
						method: 'GET',
						path: link._links.self.href
					})
			);
		}).then(linkPromises => {
			return when.all(linkPromises);
		}).done(links => {
			this.setState({
				page: this.page,
				links: links,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}

	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}

	// tag::websocket-handlers[]
	refreshAndGoToLastPage(message) {
		follow(client, root, [{
			rel: 'links',
			params: {size: this.state.pageSize}
		}]).done(response => {
			if (response.entity._links.last !== undefined) {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		})
	}

	refreshCurrentPage(message) {
		follow(client, root, [{
			rel: 'links',
			params: {
				size: this.state.pageSize,
				page: this.state.page.number
			}
		}]).then(linkCollection => {
			this._links = linkCollection.entity._links;
			this.page = linkCollection.entity.page;

			return linkCollection.entity._embedded.links.map(link => {
				return client({
					method: 'GET',
					path: link._links.self.href
				})
			});
		}).then(linkPromises => {
			return when.all(linkPromises);
		}).then(links => {
			this.setState({
				page: this.page,
				links: links,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				_links: this._links
			});
		});
	}
	// end::websocket-handlers[]

	// tag::register-handlers[]
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
		stompClient.register([
			{route: '/topic/newLink', callback: this.refreshAndGoToLastPage},
			{route: '/topic/updateLink', callback: this.refreshCurrentPage},
			{route: '/topic/deleteLink', callback: this.refreshCurrentPage}
		]);
	}
	// end::register-handlers[]

	render() {
		return (
			<div>
				<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
				<LinkList page={this.state.page}
							  links={this.state.links}
							  _links={this.state._links}
							  pageSize={this.state.pageSize}
							  attributes={this.state.attributes}
							  onNavigate={this.onNavigate}
							  onUpdate={this.onUpdate}
							  onDelete={this.onDelete}
							  updatePageSize={this.updatePageSize}/>
			</div>
		)
	}
}

class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const newLink = {};
		this.props.attributes.forEach(attribute => {
			newLink[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onCreate(newLink);
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = ''; // clear out the dialog's inputs
		});
		window.location = "#";
	}

	render() {
		const inputs = this.props.attributes.map(attribute =>
			<p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field"/>
			</p>
		);
		return (
			<div>
				<a href="#createLink">Create</a>

				<div id="createLink" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new link</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}
}

class UpdateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const updatedLink = {};
		this.props.attributes.forEach(attribute => {
			updatedLink[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onUpdate(this.props.link, updatedLink);
		window.location = "#";
	}

	render() {
		const inputs = this.props.attributes.map(attribute =>
			<p key={this.props.link.entity[attribute]}>
				<input type="text" placeholder={attribute}
					   defaultValue={this.props.link.entity[attribute]}
					   ref={attribute} className="field"/>
			</p>
		);

		const dialogId = "updateLink-" + this.props.link.entity._links.self.href;

		return (
			<div>
				<a href={"#" + dialogId}>Update</a>

				<div id={dialogId} className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Update an link</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Update</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

}

class LinkList extends React.Component {

	constructor(props) {
		super(props);
		this.handleNavFirst = this.handleNavFirst.bind(this);
		this.handleNavPrev = this.handleNavPrev.bind(this);
		this.handleNavNext = this.handleNavNext.bind(this);
		this.handleNavLast = this.handleNavLast.bind(this);
		this.handleInput = this.handleInput.bind(this);
	}

	handleInput(e) {
		e.preventDefault();
		const pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
		if (/^[0-9]+$/.test(pageSize)) {
			this.props.updatePageSize(pageSize);
		} else {
			ReactDOM.findDOMNode(this.refs.pageSize).value = pageSize.substring(0, pageSize.length - 1);
		}
	}

	handleNavFirst(e) {
		e.preventDefault();
		this.props.onNavigate(this.props._links.first.href);
	}

	handleNavPrev(e) {
		e.preventDefault();
		this.props.onNavigate(this.props._links.prev.href);
	}

	handleNavNext(e) {
		e.preventDefault();
		this.props.onNavigate(this.props._links.next.href);
	}

	handleNavLast(e) {
		e.preventDefault();
		this.props.onNavigate(this.props._links.last.href);
	}

	render() {
		const pageInfo = this.props.page.hasOwnProperty("number") ?
			<h3>links - Page {this.props.page.number + 1} of {this.props.page.totalPages}</h3> : null;

        // list links
		const links = this.props.links.map(link =>
			<Link key={link.entity._links.self.href}
					  link={link}
					  attributes={this.props.attributes}
					  onUpdate={this.props.onUpdate}
					  onDelete={this.props.onDelete}/>
		);

		const navLinks = [];
		if ("first" in this.props._links) {
			navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
		}
		if ("prev" in this.props._links) {
			navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
		}
		if ("next" in this.props._links) {
			navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
		}
		if ("last" in this.props._links) {
			navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
		}

		return (
			<div>
				{pageInfo}
				<input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
				<table>
					<tbody>
						<tr>
							<th>short ulr</th>
							<th>long url</th>
							<th></th>
							<th></th>
						</tr>
						{links}
					</tbody>
				</table>
				<div>
					{navLinks}
				</div>
			</div>
		)
	}
}

class Link extends React.Component {

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	handleDelete() {
		this.props.onDelete(this.props.link);
	}

	render() {
		return (
			<tr>
				<td>{this.props.link.entity.shortUrl}</td>
				<td>{this.props.link.entity.longUrl}</td>
				<td>
					<UpdateDialog link={this.props.link}
								  attributes={this.props.attributes}
								  onUpdate={this.props.onUpdate}/>
				</td>
				<td>
					<button onClick={this.handleDelete}>Delete</button>
				</td>
			</tr>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)

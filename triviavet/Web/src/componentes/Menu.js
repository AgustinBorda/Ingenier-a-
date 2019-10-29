import React, {Component} from "react";
import {Link} from 'react-router-dom';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Card from "react-bootstrap/Card";
import exitlogo from './exit.png';

class Menu extends Component {

  render() {
    return (<div>

      <Navbar className="bg-light justify-content-between" fixed="top">
        <Navbar.Brand>Administracion trivia</Navbar.Brand>
        <Nav>
          <Nav.Link href="/login"><img src={exitlogo} height={40} width={40}/>
          </Nav.Link>
        </Nav>
      </Navbar>

      <Row style={{paddingTop: 60}} noGutters="true">
        <Col>
          <Navbar variant="dark" bg="dark">
            <Navbar.Brand>Categoria</Navbar.Brand>
          </Navbar>
          <div style={{padding:10}}>
            <Card border="primary">
              <Card.Header>Header</Card.Header>
              <Card.Body>
                <Card.Title>Primary Card Title</Card.Title>
                <Card.Text>
                  Some quick example text to build on the card title and make up the bulk of the card's content.
                </Card.Text>
              </Card.Body>
            </Card>
          </div>
        </Col>
        <Col>
          <Navbar variant="dark" bg="dark">
            <Navbar.Brand>Preguntas</Navbar.Brand>
          </Navbar>
          <div style={{padding:10}}>
            <Card border="primary">
              <Card.Header>Header</Card.Header>
              <Card.Body>
                <Card.Title>Primary Card Title</Card.Title>
                <Card.Text>
                  Some quick example text to build on the card title and make up the bulk of the card's content.
                </Card.Text>
              </Card.Body>
            </Card>
          </div>
        </Col>
        <Col>
          <Navbar variant="dark" bg="dark">
            <Navbar.Brand>Respuestas</Navbar.Brand>
          </Navbar>
          <div style={{padding:10}}>
            <Card border="primary">
              <Card.Header>Header</Card.Header>
              <Card.Body>
                <Card.Title>Primary Card Title</Card.Title>
                <Card.Text>
                  Some quick example text to build on the card title and make up the bulk of the card's content.
                </Card.Text>
              </Card.Body>
            </Card>
          </div>
        </Col>
      </Row>
    </div>);
  }
}
export default Menu;

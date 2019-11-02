import React, {Component} from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Card from "react-bootstrap/Card";
import exitlogo from './exit.png';
import {AsyncStorage} from "AsyncStorage";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Link } from 'react-router-dom';
import logo from './logo.png';
class Menu extends Component {

  constructor(props){
    super(props);
    this.state = {
      categories: [],
      questions : [],
    }
  this._loadCategories = this._loadCategories.bind(this);
  this._deleteCategory = this._deleteCategory.bind(this);
  this._modifyCategory = this._modifyCategory.bind(this);
  this._deleteQuestion = this._deleteQuestion.bind(this);
  }

  componentDidMount(){
    this._loadCategories();
    this._loadQuestions();
  }

  async _loadCategories() {
    const token =  await AsyncStorage.getItem('userToken');
      await fetch(process.env.REACT_APP_API_HOST + "/logged/category", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
        'Authorization' : token
      },method: 'GET',
      mode: "cors",
      })
    .then(async response => {
      const resp = await response.json();
      this.setState({ categories: resp.categories});

    })
    .catch(error => {
      console.log(error);
    });
  }

  async _loadQuestions(s) {

    const token =  await AsyncStorage.getItem('userToken');
    const admin =  await AsyncStorage.getItem('isAdmin');
    await fetch(process.env.REACT_APP_API_HOST + "/admin/listquestion", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
        'Authorization' : token,
        'IsAdmin': admin,
      },method: 'POST',
      body:  JSON.stringify({'category': s}),
      mode: 'cors',
      })
    .then(async response => {
      let resp = await response.json();
      console.log(resp);
      this.setState({ questions: resp.questions});

    })
    .catch(error => {
      console.log(error);
    });
  }
  async _deleteQuestion(message) {
    const token =  await AsyncStorage.getItem('userToken');
    const isAdmin = await AsyncStorage.getItem('isAdmin');
    await fetch(process.env.REACT_APP_API_HOST + "/admin/removequestion", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
        'Authorization' : token,
        'IsAdmin' : isAdmin,
        },
      method: 'POST',
      body: JSON.stringify({
        description : message.toString(),
      }),
      mode: "cors",
      })
    .then(async response => {
        const res = await response.json();
        this._loadQuestions(res.cat);
    })
    .catch(error => {
      console.log(error);
    });

  }

  async _modifyCategory(message) {
    AsyncStorage.setItem("old_name", message);
    this.props.history.push("/modifyCategory")
  }

  async _deleteCategory(message) {
    const token =  await AsyncStorage.getItem('userToken');
    const isAdmin = await AsyncStorage.getItem('isAdmin');
    await fetch(process.env.REACT_APP_API_HOST + "/admin/deletecategory", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
        'Authorization' : token,
        'IsAdmin' : isAdmin,
        },
      method: 'POST',
      body: JSON.stringify({
        name : message.toString(),
      }),
      mode: "cors",
      })
    .then(async response => {
        this._loadCategories();
    })
    .catch(error => {
      console.log(error);
    });

  }


  render() {
    return (
      <div>
        <Navbar className="bg-light justify-content-between" fixed="top">
                    <img src={logo}
                height={60}
                 width={60}/>
        <Navbar.Brand>Administracion trivia</Navbar.Brand>

        <Nav>
          <Nav.Link href="/login"><img src={exitlogo} height={40} width={40}/>
        </Nav.Link>
        </Nav>
      </Navbar>
        <Row style={{paddingTop: 60}} noGutters="true">
        <Col>
          <Navbar variant="dark" bg="dark"className="justify-content-between">
            <Navbar.Brand>Categoria</Navbar.Brand>
             <Link to="/NewCategory" className="NewCategory">
                <Button variant="primary" type="submit">
                 +
                </Button>
             </Link>
          </Navbar>

            {this.state.categories.map((message) =>
              <div style={{padding:10}}>
                
                <Card id={message} onClick={(x) => this._loadQuestions(message)} border="secondary">

                  <Card.Header>{message}
                  <div>
                    <Button onClick={() => this._deleteCategory(message)} variant ="primary" type="submit">
                        -
                    </Button>
                    <Button onClick={() => this._modifyCategory(message)} variant ="primary" type="submit">
                        Modificar
                    </Button>
                  </div>
                  </Card.Header>
                </Card>
                </div>
            )}
        </Col>
        <Col>
          <Navbar variant="dark" bg="dark" className="justify-content-between">
            <Navbar.Brand>Preguntas</Navbar.Brand>
             <Link to="/newQuestion" className="NewQuestion">
                <Button variant="primary" type="submit">
                 +
                </Button>
             </Link>
          </Navbar>
          {this.state.questions.map((message) =>
            <div style={{padding:10}}>
              <Card id={message} border="secondary">
                <Card.Header>{message}</Card.Header>
               <Button onClick={() => this._deleteQuestion(message)} variant ="primary" type="submit">
                   -
               </Button>
              </Card>
              </div>
          )}
        </Col>
        <Col>
          <Navbar variant="dark" bg="dark" className="justify-content-between">
            <Navbar.Brand>Estadisticas</Navbar.Brand>
             <Link to="/Stadistics" className="Stadistics">
                <Button variant="primary" type="submit">
                 Ver Estadisticas
                </Button>
             </Link>
          </Navbar>
          <div style={{padding:10}}>
            <Card border="primary">
              <Card.Header>Header</Card.Header>
              <Card.Body>
                <Card.Title>Primary Card Title</Card.Title>
                <Card.Text>
                  
                </Card.Text>
              </Card.Body>
            </Card>
          </div>
        </Col>
      </Row>
      </div>
    );
  }
}
export default Menu;

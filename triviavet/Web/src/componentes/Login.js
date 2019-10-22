import React, {Component} from "react";
import {AsyncStorage} from "AsyncStorage";
import {StyleSheet, StyleResolver} from "style-sheet";
import ReactDOM from "react-dom";
import Menu from './Menu';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import CreateAccount from './CreateAccount';
import {Link} from 'react-router-dom';
import logo from './logo.png';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: ''
    };

    this._login = this._login.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    }, () => {
      //updated state
      console.log(this.state)
    });
  }
  handleSubmit(event) {
    alert('A user was submitted: ' + this.state.username);
    event.preventDefault();
  }

  async _login() {
    await fetch(process.env.REACT_APP_API_HOST + "/login", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
      },
      method: 'POST',
      body: JSON.stringify({username: this.state.username, password: this.state.password}),
      mode: "no-cors",
      cache: "default"
    })
  //  .then(response => response.json())
     .then(response => {
      console.log(response.json());
      AsyncStorage.setItem('userToken', this.state);
      ReactDOM.render(<Menu/>, document.getElementById('root'))
    });
  }
  /*<img src={logo} className="App-logo" alt="logo" style={{
      width: 200,
      height: 200
    }}/>*/
    render() {

      return (<div className={StyleResolver.resolve([styles.app])}>


        <div className={StyleResolver.resolve([styles.layout, styles.container])}>
          <div css={{
              fontFamily: "monaco, monospace",
              color: "#1e252d"
            }}>
            <h1>
              Login</h1>
            <Form onSubmit={this.handleSubmit}>
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Username</Form.Label>
                <Form.Control name="username" type="Username" placeholder="Username" bsSize="large" username={this.state.username} onChange={this.handleChange}/>
                <Form.Text className="text-muted"></Form.Text>
              </Form.Group>

              <Form.Group controlId="formBasicEmail">
                <Form.Label>Password</Form.Label>
                <Form.Control name="password" type="password" placeholder="Password" password={this.state.password} onChange={this.handleChange}/>
              </Form.Group>

              <Button onClick={this._login} variant="primary" type="submit">
                Iniciar Sesion
              </Button>
              <p></p>
              <p></p>
              <Link to="/CreateAccount" className="CreateAccount">
                <Button variant="primary" type="submit">
                  Crear Cuenta
                </Button>
              </Link>
            </Form>
        </div>
      </div>
    </div>);
    }

  }

  export default Login;

  const styles = StyleSheet.create({
    layout: {
      width: "100%",
      maxWidth: "640px"
    },
    container: {
      padding: "2em",
      border: "1px solid",
      borderRadius: "3px",

      backgroundColor: "rgba(114,137,218, 0.2)",
      boxShadow: "0 2px 30px 6px #000000",
      transition: "transform 0.2s ease-out",
      "&:hover": {
        transform: "scale(1.1)"
      }
    },
    app: {
      background: "radial-gradient(circle, rgba(35,39,42,1) 0%, rgba(44,47,51,1) 100%)",
      height: "100vh",
      width: "100vw",
      padding: "2rem",
      boxSizing: "border-box",
      display: "flex",
      alignItems: "center",
      justifyContent: "center"
    }
  });

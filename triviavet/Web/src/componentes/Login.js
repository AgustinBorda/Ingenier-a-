import React,{Component,Redirect,alert} from "react";
import {AsyncStorage} from "AsyncStorage";
import ReactDOM from "react-dom";
import Menu from './Menu';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import CreateAccount from './CreateAccount';
import { Link } from 'react-router-dom';



class Login extends Component{
    constructor(props) {
        super(props);
        this.state = {username: '',password: ''};

        this._login = this._login.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }

      handleChange=(event)=> {
        this.setState({[event.target.name]: event.target.value},
        () => {
          //updated state
          console.log(this.state)
        });
      }
      handleSubmit(event) {
        alert('A user was submitted: ' + this.state.username);
        event.preventDefault();
      }

        async _login(){
          await fetch(process.env.REACT_APP_API_HOST+"/login",{
              method: 'POST',
              body: JSON.stringify({
                username : this.state.username,
                password : this.state.password
                }),
              mode: "no-cors",
              cache: "default"
            })
            .then(response => {
              alert(response);
                AsyncStorage.setItem('userToken', response.config.headers.Authorization);
                ReactDOM.render(
                   <Menu/>,
                    document.getElementById('root')
                )
              })
              .catch(error => {
                console.log(error)
              });

      }

  render () {
        return (

             <form onSubmit={this.handleSubmit}>
             <h1> Login</h1>

             <Form>
              <Form.Group controlId="formBasicEmail">
              <Form.Label>Username</Form.Label>
              <Form.Control name="username" type="Username" placeholder="Username" bsSize="large"
              username={this.state.username} onChange={this.handleChange}/>
              <Form.Text className="text-muted">

              </Form.Text>
              </Form.Group>

              <Form.Group controlId="formBasicEmail" >
                <Form.Label>Password</Form.Label>
                <Form.Control name="password" type="password" placeholder="Password"
                password={this.state.password} onChange={this.handleChange}/>
              </Form.Group>

               <Button onClick = {this._login} variant="primary" type="submit">
                  Iniciar Sesion
              </Button>
              <p>   </p>
              <p>   </p>
              <Link to="/CreateAccount" className="CreateAccount">
               <Button variant="primary" type="submit">
                  Crear Cuenta
               </Button>
               </Link>
            </Form>
        </form>
        );
  }
}


export default Login;


/*
     <div className="Login">
     <form onSubmit={this.handleSubmit}>
      <Form>
      <Form.Group controlId="formBasicEmail">
      <Form.Label>Username</Form.Label>
      <Form.Control type="Username" placeholder="Enter Username" bsSize="large"
      username={this.state.username} onChange={this.handleChange}/>
      <Form.Text className="text-muted">

      </Form.Text>
      </Form.Group>

      <Form.Group controlId="formBasicPassword">
        <Form.Label>Password</Form.Label>
        <Form.Control type="password" placeholder="Password"
        password={this.state.password} onChange={this.handleChange}/>
      </Form.Group>

       <Button variant="primary" type="submit">
        Submit
      </Button>
  </Form>
 */
 /* <form onSubmit={this.handleSubmit}>
            <label>
            username:
            <input type="text" username={this.state.username} onChange={this.handleChange} />
            </label>
            <label>
            password:
            <input type="password" password={this.state.password} onChange={this.handleChange} />
            </label>
            <button type="submit"> iniciar sesion</button>
        </form>*/

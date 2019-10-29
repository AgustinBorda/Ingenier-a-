import React,{Component} from "react";
import { Link } from 'react-router-dom';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {AsyncStorage} from "AsyncStorage";
import ReactDOM from "react-dom";
import Menu from './Menu';
import {StyleSheet, StyleResolver} from "style-sheet";
import logo from './logo.png';


class CreateAccount extends Component{
	constructor(props) {
        super(props);
        this.state = {username: '', password: '', email:''};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
				this._createAccount = this._createAccount.bind(this);
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
        event.preventDefault();
      }

      _createAccount(){

          fetch(process.env.REACT_APP_API_HOST+"/users",{
							headers : {
								'Accept' : 'application/json',
								'content-type' : 'application/json',
							},
              method: 'POST',
              body: JSON.stringify({
								username: this.state.username,
								password: this.state.password,
               	email: this.state.email
							}),
              mode: "cors"
            })
            .then(response => {
							if(response.ok) {
								AsyncStorage.setItem('userToken', {
										username: this.state.username,
										password: this.state.password
									});
									this.props.history.push("/menu")
							} else {
								if(response.status == 403) {
									alert("Todos los datos deben ser rellenados");
								} else {
									alert("Ya existe un usuario con ese nombre o email");
								}
							}
              })
              .catch(error => {
                console.log(error)
              });

      }


  render () {
    return (
          <div className={StyleResolver.resolve([styles.app])}>

          <div className={StyleResolver.resolve([styles.layout, styles.container])}>

          <div css={{
              fontFamily: "monaco, monospace",
              color: "#1e252d"
            }}>
            <div>
              <h1> <img src={logo}
                height={60}
                 width={60}/>CreateAccount</h1>
            </div>
          <form onSubmit={this.handleSubmit}>
            <Form>
              <Form.Group controlId="formBasicEmail">
              <Form.Label>Username</Form.Label>
              <Form.Control name="username" type="Username" placeholder="Enter Username" bsSize="large"
              username={this.state.username} onChange={this.handleChange}/>
              <Form.Text className="text-muted">

              </Form.Text>
              </Form.Group>

              <Form.Group controlId="formBasicPassword" >
                <Form.Label>Password</Form.Label>
                <Form.Control name="password" type="password" placeholder="Password"
                password={this.state.password} onChange={this.handleChange}/>
              </Form.Group>

               <Form.Group controlId="formBasicEmail" >
                <Form.Label>Email</Form.Label>
                <Form.Control name="email" type="email" placeholder="Email"
                password={this.state.email} onChange={this.handleChange}/>
              </Form.Group>import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

		   <Button onClick={this._createAccount} variant="primary" type="submit">
                Crear Cuenta
           </Button>
           <p></p>
           <p></p>
           <Link to="/login" className="Login">
            <Button variant="primary" type="submit">
                 Atras
            </Button>
           </Link>
            </Form>
 		     </form>
         </div>
       </div>
     </div>
     );
  }
}


export default CreateAccount;
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

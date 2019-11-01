import React,{Component, Redirect} from "react";
import {AsyncStorage} from "AsyncStorage";
import ReactDOM from "react-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Menu from './Menu';
import { Link } from 'react-router-dom';
import {StyleSheet, StyleResolver} from "style-sheet";
import logo from './logo.png';

class NewCategory extends Component{
    constructor(props) {
        super(props);
        this.state = {description: ''};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this._createCategory = this._createCategory.bind(this);
      }

      handleChange = (event) => {
		    this.setState({
		      [event.target.name]: event.target.value
		    });
		  }

      handleSubmit(event) {
        event.preventDefault();
      }

      async _createCategory(){
        const token = await AsyncStorage.getItem('userToken');
        const isAdmin = await AsyncStorage.getItem('isAdmin');
        await fetch(process.env.REACT_APP_API_HOST+"/admin/createcategory",{
            method: 'POST',
            headers : {
							'Accept' : 'application/json',
							'content-type' : 'application/json',
              'Authorization' : token,
              'IsAdmin' : isAdmin,
						},
            mode: 'cors',
            body: JSON.stringify({
            name: this.state.description
            }),
          })
          .then(async response => {
              console.log(response.ok);
              if(response.status == 200) {
              alert("Categoria creada");
              this.props.history.push("/menu")
              }
              else {
                alert("No se pudo crear la categoria");
              }
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
                 width={60}/> Crear nueva Categoria</h1>
            </div>
          <form onSubmit={this.handleSubmit}>
            <Form>
              <Form.Group controlId="formBasicEmail">
              <Form.Label>Username</Form.Label>
              <Form.Control name="description" type="Text" placeholder="Categoria" bsSize="large"
              username={this.state.username} onChange={this.handleChange}/>
              <Form.Text className="text-muted">

              </Form.Text>
              </Form.Group>

		       <Button onClick={this._createCategory} variant="primary" type="submit">
                Crear Categoria
           </Button>
           <p></p>
           <p></p>
           <Link to="/menu" className="Menu">
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


export default NewCategory;
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

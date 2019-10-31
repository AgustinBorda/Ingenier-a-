import React,{Component, Redirect} from "react";
import {AsyncStorage} from "AsyncStorage";
import ReactDOM from "react-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Menu from './Menu';
import { Link } from 'react-router-dom';
import {StyleSheet, StyleResolver} from "style-sheet";
import logo from './logo.png';


class NewQuestion extends Component{
    constructor(props) {
        super(props);
        this.state = {description: '',option1: '',option2:'',option3:'',optionCorrect:''};

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

      _createQuestion(){
          var c= 'Ciencia';
          fetch(process.env.REACT_APP_API_HOST+"/admin/questions",{
              method: 'POST',
              body: {description: this.state.description, option1: this.state.option1,
                option2: this.state.option2, option3: this.state.option3, optionCorrect:this.state.optionCorrect, cat:c},
                headers: { 'Authorization' : AsyncStorage.getItem('userToken')}
            }).then(response => response.json())
            .then(response => {
                AsyncStorage.setItem('userToken', response.config.headers.Authorization);
                ReactDOM.render(
                    <Redirect to="/menu" />,
                    document.getElementById('root')
                )
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
                 width={60}/> Crear Pregunta </h1>
            </div>
            <Form onSubmit={this.handleSubmit}>

              <Form.Group controlId="formBasicEmail">
                <Form.Label>Descripcion</Form.Label>
                <Form.Control name="description" type="description" placeholder="description" bsSize="large" 
                description={this.state.description} onChange={this.handleChange}/>
                <Form.Text className="text-muted"></Form.Text>
              </Form.Group>

          	  <Form.Group controlId="formBasicEmail">
                <Form.Label>option1</Form.Label>
                <Form.Control name="option1" type="option1" placeholder="option1" 
                option1={this.state.option1} onChange={this.handleChange}/>
               </Form.Group>
			  
			  <Form.Group controlId="formBasicEmail">
                <Form.Label>option2</Form.Label>
                <Form.Control name="option2" type="option2" placeholder="option2" 
                option1={this.state.option1} onChange={this.handleChange}/>
               </Form.Group>
			
			  <Form.Group controlId="formBasicEmail">
                <Form.Label>option3</Form.Label>
                <Form.Control name="option3" type="option3" placeholder="option3" 
                option1={this.state.option1} onChange={this.handleChange}/>
               </Form.Group>

               <Form.Group controlId="formBasicEmail">
                <Form.Label>Opcion 4</Form.Label>
                <Form.Control name="optionCorrect" type="optionCorrect" placeholder="optionCorrect" 
                optionCorrect={this.state.optionCorrect} onChange={this.handleChange}/>
              </Form.Group>

              <Button onClick={this._login} variant="primary" type="submit">
                Crear Pregunta
              </Button>

              <Link to="/menu" className="Menu">
                <Button variant="secondary" type="submit">
                 Atras
                </Button>
              </Link>
            </Form>
        </div>
      </div>
    </div>);''
  }
}


export default NewQuestion;
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
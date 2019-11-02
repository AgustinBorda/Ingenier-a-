import React,{Component, Redirect} from "react";
import {AsyncStorage} from "AsyncStorage";
import ReactDOM from "react-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Menu from './Menu';
import { Link } from 'react-router-dom';
import logo from './logo.png';
import {StyleSheet, StyleResolver} from "style-sheet";

class ModifyQuestion extends Component{
    constructor(props) {
        super(props);
        this.state = {
          description:'',
          option1:'',
          option2:'',
          option3:'',
          optionCorrect:''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this._modifyQuestion = this._modifyQuestion.bind(this);
      }

      handleChange = (event) => {
		    this.setState({
		      [event.target.name]: event.target.value
		    })
		  }

      handleSubmit(event) {
        event.preventDefault();
      }

     async  _modifyQuestion(){
          const oldName = await AsyncStorage.getItem("old_name");
          const token = await AsyncStorage.getItem('userToken');
          const isAdmin = await AsyncStorage.getItem('isAdmin');
          const cat = await AsyncStorage.getItem('choosenCategory');
          fetch(process.env.REACT_APP_API_HOST+"/admin/modifyquestion",{
              method: 'POST',
              body: JSON.stringify({
                oldDescription: oldName,
                modifiedQuestion :{
                  description : this.state.description,
                  category: cat,
                  options: [{description:this.state.option1, correct: false}, 
                            {description:this.state.option2, correct:false}, 
                            {description:this.state.option3, correct:false},
                            {description:this.state.optionCorrect, correct:true}],
                },
              }),
                headers: {
                  'Accept' : 'application/json',
                  'content-type' : 'application/json',
                  'Authorization' : token,
                  'IsAdmin' : isAdmin,
              },
              mode : 'cors'
            })
            .then(async response => {
                const resp = await response.json();
                if(response.ok){
                  alert("Pregunta Modificada");
                  this.props.history.push("/menu");
                }
                else{
                  alert("No se pudo modificar la Pregunta");
                  this.props.history.push("/menu");
                }
         });

      }

  render () {
    return (<div className={StyleResolver.resolve([styles.app])}>


      <div className={StyleResolver.resolve([styles.layout, styles.container])}>
        <div css={{
            fontFamily: "monaco, monospace",
            color: "#1e252d"
          }}>
          <div>
            <h1> <img src={logo}
              height={60}
               width={60}/> Modificar Categoria</h1>
          </div>
          <Form onSubmit={this.handleSubmit}>
            <Form.Group controlId="formBasicEmail">
              <Form.Label>Descripcion</Form.Label>
              <Form.Control name="description" type="Text" placeholder="Descripcion"
               bsSize="large" question={this.state.question} onChange={this.handleChange}/>
              <Form.Text className="text-muted"></Form.Text>
            </Form.Group>

              <Form.Group controlId="formBasicEmail">
                <Form.Label>Opcion 1</Form.Label>
                <Form.Control name="option1" type="option1" placeholder="option1" 
                option1={this.state.option1} onChange={this.handleChange}/>
               </Form.Group>
        
               <Form.Group controlId="formBasicEmail">
                <Form.Label>Opcion 2</Form.Label>
                <Form.Control name="option2" type="option2" placeholder="option2" 
                option2={this.state.option2} onChange={this.handleChange}/>
               </Form.Group>
      
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Opcion 3</Form.Label>
                <Form.Control name="option3" type="option3" placeholder="option3" 
                option3={this.state.option3} onChange={this.handleChange}/>
               </Form.Group>

               <Form.Group controlId="formBasicEmail">
                <Form.Label>Opcion Correcta</Form.Label>
                <Form.Control name="optionCorrect" type="optionCorrect" placeholder="optionCorrect" 
                optionCorrect={this.state.optionCorrect} onChange={this.handleChange}/>
              </Form.Group>

            <Button onClick={this._modifyQuestion} variant="primary" type="submit">
              modificar Pregunta
            </Button>
            <p></p>
            <p></p>
            <div>
            <Link to="/menu" className="Menu">
              <Button variant="secondary" type="submit">
                Atras
              </Button>
            </Link>
          </div>
          </Form>
      </div>
    </div>
  </div>);
  }
}


export default ModifyQuestion;
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

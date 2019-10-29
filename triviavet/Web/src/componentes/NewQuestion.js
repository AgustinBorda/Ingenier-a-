import React,{Component, Redirect} from "react";
import {AsyncStorage} from "AsyncStorage";
import ReactDOM from "react-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Menu from './Menu';
import { Link } from 'react-router-dom';

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
            <form onSubmit={this.handleSubmit}>
            <Form>
                <Form.Group controlId="formBasicPassword" >
                <Form.Label>Descripcón</Form.Label>
                <Form.Control type="description" placeholder="description"
                password={this.state.description} onChange={this.handleChange}/>
              </Form.Group>

            <label>
            Opción correcta:
            <input type="text" optionCorrect={this.state.optionCorrect} onChange={this.handleChange} />
            </label>
            <label>
            Opción:
            <input type="text" option1={this.state.option1} onChange={this.handleChange} />
            </label>
            <label>
            Opción:
            <input type="text" option2={this.state.option2} onChange={this.handleChange} />
            </label>
            <label>
            Opción:
            <input type="text" option3={this.state.option3} onChange={this.handleChange} />
            </label>
             <p>   </p>
              <p>   </p>
             <Button variant="primary" type="submit">
                 Guardar
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
        );
  }
}


export default NewQuestion;

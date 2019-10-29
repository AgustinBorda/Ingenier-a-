import {BrowserRouter, Route, Switch, Redirect, Link} from 'react-router-dom';
import React, {Component} from "react";
import Menu from './componentes/Menu';
import CreateAccount from './componentes/CreateAccount';
import './App.css';
import Login from './componentes/Login';
import NewQuestion from './componentes/NewQuestion';
import NewCategory from './componentes/NewCategory';


class App extends Component {

  render() {
    require('dotenv').config();
    return (
    <BrowserRouter>
      <Switch>
          <Redirect exact from="/" to="/login"/>
          <Route path="/newQuestion" component={NewQuestion}/>
          <Route path="/newCategory" component={NewCategory}/>
          <Route path="/login" component={Login}/>
          <Route path="/menu" component={Menu}/>
          <Route path="/CreateAccount" component={CreateAccount}/>
        </Switch>
    </BrowserRouter>);
  }
}

export default App;

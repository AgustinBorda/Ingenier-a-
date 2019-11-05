import {BrowserRouter, Route, Switch, Redirect, Link} from 'react-router-dom';
import React, {Component} from "react";
import Menu from './componentes/Menu';
import CreateAccount from './componentes/CreateAccount';
import './App.css';
import Login from './componentes/Login';
import NewQuestion from './componentes/NewQuestion';
import NewCategory from './componentes/NewCategory';
import Stadistics from './componentes/Stadistics';
import StatForUser from './componentes/StatForUser';
import AllStatistics from './componentes/AllStatistics';
import ModifyCategory from './componentes/ModifyCategory';
import ModifyQuestion from './componentes/ModifyQuestion';

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
          <Route path="/Stadistics" component={Stadistics}/>
          <Route path="/StatForUser" component={StatForUser}/>
          <Route path="/AllStatistics" component={AllStatistics}/>
          <Route path="/ModifyCategory" component={ModifyCategory}/>
          <Route path="/ModifyQuestion" component={ModifyQuestion}/>

        </Switch>
    </BrowserRouter>);
  }
}

export default App;

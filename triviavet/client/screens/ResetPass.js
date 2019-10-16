import React from 'react';
import { API_HOST } from 'react-native-dotenv';
import {
  AsyncStorage,
  View,
  Text,
  TextInput,
  Button,
  TouchableOpacity,
  StyleSheet,
} from 'react-native';
import axios from 'axios';

export default class ResetPass extends React.Component {
  static navigationOptions = {
    title: 'Reset password',
    headerTintColor: '#fff',
  };

  constructor(props) {
    super(props);
    this.state = {
      username: ''
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.text}>
        Ingrese su nombre de usuario y se le enviara un email con un codigo de recuperacion.
        </Text>

        <TextInput
          placeholder="Username"
          autoCompleteType='username'
          style={styles.input}
          onChangeText={(value) => this.setState({ username: value })}
          value={this.state.username}
        />
         <View style={{margin:20}} />

        <Button title="Enviar" onPress={this._sendUsername}
          color = "#ebee2c"
        />
         <View style={{margin:20}} />
        <Button title="Ya tengo codigo" onPress={() => this.props.navigation.navigate('InsertCode')}
          color = "#ebee2c"
        />
        <View style={{margin:20}} />
       <Button title="Volver" onPress={() => this.props.navigation.navigate('Auth')}
         color = "#ebee2c"
       />
      </View>
    );
  }

  _sendUsername = () => {
    const { username } = this.state;

    axios.post(API_HOST+"/reset", {username: username});
    this.props.navigation.navigate('InsertCode');
  };

}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#48c9b0',
  },
  text: {
    fontSize: 20,
    textAlign: 'justify',
    margin: 10,
    color:'#ffffff',
  },
  input: {
    margin: 15,
    height: 40,
    padding: 5,
    fontSize: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#eceef1'
  }
})

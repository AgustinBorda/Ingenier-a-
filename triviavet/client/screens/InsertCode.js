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

export default class InsertCode extends React.Component {
  static navigationOptions = {
    title: 'Insert code',
  };

  constructor(props) {
    super(props);
    this.state = {
      code: '',
      newPass: ''
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.text}>
          Ingrese su codigo de recuperacion y su nueva contraseña.
        </Text>

        <TextInput
          placeholder="Codigo"
          style={styles.input}
          onChangeText={(value) => this.setState({ code: value })}
          value={this.state.code}
        />
         <View style={{margin:20}} />
         <TextInput
           placeholder="Nueva contraseña"
           style={styles.input}
           autoCompleteType='password'
           secureTextEntry={true}
           onChangeText={(value) => this.setState({ newPass: value })}
           value={this.state.newPass}
         />
          <View style={{margin:20}} />

        <Button title="Cambiar" onPress={this._sendNewPass}
          color = "#ebee2c"
        />
         <View style={{margin:20}} />
        <Button title="Volver" onPress={() => this.props.navigation.navigate('Auth')}
          color = "#ebee2c"
        />
      </View>
    );
  }

  _sendNewPass = () => {
    const {code, newPass} = this.state;

    if (code === '' || newPass === '') {
        alert("Complete los campos");
        return;
    }

    axios.post(API_HOST+"/newPass", {
      code: code,
      newPass: newPass
    })
    .catch((error) => {
      if(error.toString().match(/401/)) {
        alert("Codigo incorrect");
        return;
      }
      alert(API_HOST+"\n"+error);
    });
    alert("Contraseña cambiada");
    this.props.navigation.navigate('Auth');

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

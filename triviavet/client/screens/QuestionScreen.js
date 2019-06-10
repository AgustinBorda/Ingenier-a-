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

export default class SignInScreen extends React.Component {
  static navigationOptions = {
    title: '',
  };

  constructor(props) {
    super(props);
    this.state = {
      question: "",
      option: ""
    }
  }

  async componentWillMount () {
    axios.get("http://192.168.0.170:4567/question",{
      headers:{'Authorization' : await AsyncStorage.getItem('userToken')}
    })
    .then(response => JSON.parse(JSON.stringify(response)))
    .then(response => {
      // Handle the JWT response here
      this.setState({question: response.data})
    })
    .catch((error) => {
      if(error.toString().match(/500/)) {
        alert("Error interno del servidor");
        this.props.navigation.navigate('Play')
        return;
      }
      alert(error);
    });
  }


  _getCorrect = async () => {
      axios.post("http://192.168.0.170:4567/answer", {
        answer: this.state.option.toString()
      },{
        headers:{'Authorization' : await AsyncStorage.getItem('userToken')}
      })
      .then(response => JSON.parse(JSON.stringify(response)))
      .then(response => {
        // Handle the JWT response here
        console.log(response.data);
        alert(response.data.answer);
        this.props.navigation.navigate('Play')
      })
      .catch((error) => {
        if(error.toString().match(/500/)) {
          alert("Error interno del servidor");
          this.props.navigation.navigate('Play')
        }
        alert(error);
      })
  }


  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.question}>
          {this.state.question.description}
        </Text>
        <Text style={styles.answer} onPress={async () =>{await this.setState({option: "1"})}}>
          {this.state.question.answer1}
        </Text>
        <Text style={styles.answer} onPress={this.correct2}>
          {this.state.question.answer2}
        </Text>
        <Text style={styles.answer} onPress={this.correct3}>
          {this.state.question.answer3}
        </Text >
        <Text style={styles.answer} onPress={this.correct4}>
          {this.state.question.answer4}
        </Text>
        <Button
          onPress={() => this.props.navigation.navigate('Play')}
          title="<-"
          color="#a4f590"
          accessibilityLabel="Learn more about this button"
        />

      </View>
    );
  }

}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#8080F0',
  },
  question: {
    fontSize: 30,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#FFFFFF',
  },
  answer: {
    fontSize: 15,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#FFFFFF',
  },
  input: {
    margin: 20,
    height: 40,
    padding: 5,
    fontSize: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#4228F8'
  }
})

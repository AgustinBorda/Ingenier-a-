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

export default class QuestionScreen extends React.Component {
  static navigationOptions = {
    title: 'Play',

    category: AsyncStorage.getItem('category')
  };

  constructor(props) {
    super(props);
    this.state = {
      question: "",
      option: ""
    }
  }

  async componentWillMount () {
    const cat = await AsyncStorage.getItem('category');
    const token =  await AsyncStorage.getItem('userToken');
    await axios.post(API_HOST+"/logged/question",{
      category: cat
    },{
      headers:{'Authorization' : token}
    })
    .then(response => JSON.parse(JSON.stringify(response)))
    .then(response => {
      // Handle the JWT response here
      AsyncStorage.removeItem("category");
      this.setState({question: response.data})
    })
    .catch((error) => {
      if(error.toString().match(/500/)) {
        alert("No hay mas preguntas");
        AsyncStorage.removeItem("category");
        this.props.navigation.navigate('Play')
        return;
      }
      console.log(error);
    });
  }



  _getCorrect = async (res) => {
      axios.post(API_HOST+"/logged/answer", {
        answer: res
      },{
        headers:{'Authorization' : await AsyncStorage.getItem('userToken')}
      })
      .then(response => JSON.parse(JSON.stringify(response)))
      .then(response => {
        // Handle the JWT response here
        alert(response.data.answer);
        this.props.navigation.navigate('Play')
      })
      .catch((error) => {
        console.log("Momento ideal para romperse");
        if(error.toString().match(/500/)) {
          this.props.navigation.navigate('Play')
        }
        alert(error);
      })
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.question}>
        <Text style={styles.tabBarInfoText}>
          {this.state.question.description}
        </Text>
        </Text>
        <Text style={styles.answer} onPress={this._getCorrect.bind(this, '1')}>
          {this.state.question.answer1}
        </Text>
        <Text style={styles.answer} onPress={this._getCorrect.bind(this, '2')}>
          {this.state.question.answer2}
        </Text>
        <Text style={styles.answer} onPress={this._getCorrect.bind(this, '3')}>
          {this.state.question.answer3}
        </Text>
         <Text style={styles.answer} onPress={this._getCorrect.bind(this, '4')}>
          {this.state.question.answer4}
        </Text>        
        <Button
          onPress={() => this.props.navigation.navigate('Play')}
          title="Volver"
          color="#d35400"
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
    backgroundColor: '#1b4f72',
  },
  question: {
    fontSize: 40,
    textAlign: 'center',
    margin: 30,
    color: '#FFFFFF',
    backgroundColor: '#1b4f72',
  },
  answer: {
    fontSize: 20,
    textAlign: 'center',
    margin: 20,
    color: '#FFFFFF',
    backgroundColor: '#1b4f72',
  },
  input: {
    margin: 20,
    height: 40,
    padding: 5,
    fontSize: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#fff933'
  },
  tabBarInfoText: {
    fontSize: 25,
    color: '#FFFFFF',
    textAlign: 'center',
   } 
})

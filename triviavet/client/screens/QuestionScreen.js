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
    title: '',
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
    if(cat===null){
      await axios.get(API_HOST+"question",{

        headers:{'Authorization' : token}
      })
      .then(response => JSON.parse(JSON.stringify(response)))
      .then(response => {
        // Handle the JWT response here
        this.setState({question: response.data})
      })
      .catch((error) => {
        if(error.toString().match(/500/)) {
          alert("No hay mas preguntas");
          this.props.navigation.navigate('Play')
          return;
        }
        alert(error);
      });
    }
    else{
      await axios.post(API_HOST+"/categoryquestion",{

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
  }



  _getCorrect = async (res) => {
      axios.post(API_HOST+"/answer", {
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
          {this.state.question.description}
        </Text>
        <Text style={styles.answer} onPress={this._getCorrect.bind(this, '1')}>
          {this.state.question.answer1}
        </Text>
        <Text style={styles.answer} onPress={this._getCorrect.bind(this, '2')}>
          {this.state.question.answer2}
        </Text>
        <Text style={styles.answer} onPress={this._getCorrect.bind(this, '3')}>
          {this.state.question.answer3}
        </Text >
        <Text style={styles.answer} onPress={this._getCorrect.bind(this, '4')}>
          {this.state.question.answer4}
        </Text>
        <Button
          onPress={() => this.props.navigation.navigate('Play')}
          title="<-"
          color="#000000"
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
    backgroundColor: '#FFFFFF',
  },
  question: {
    fontSize: 30,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#FFFFFF',
  },
  answer: {
    fontSize: 20,
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

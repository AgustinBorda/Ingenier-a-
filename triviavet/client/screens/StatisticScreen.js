import React from 'react';
import { API_HOST } from 'react-native-dotenv';
import {
  AsyncStorage,
  View,
  ScrollView,
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
      stats: ""
    }
  }

  async componentWillMount () {
    const token =  await AsyncStorage.getItem('userToken')
    await axios.get(API_HOST+"/logged/statistics",{
      headers:{'Authorization' : token}})
    .then(
      response => JSON.parse(JSON.stringify(response))
    )
    .then(response => {
      // Handle the JWT response here
    this.setState({stats: response.data})
    })
    .catch((error) => {
      if(error.toString().match(/500/)) {
        alert("Error interno del servidor");
        this.props.navigation.navigate('App')
        return;
      }
      alert(error);
    });
  }


  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>
          Estadisticas:
        </Text>
      <ScrollView>
          <Text style={styles.cat}>
          Anatomia
          </Text>
          <Text style={styles.content}>
            Puntos: {this.state.stats.points0}
          </Text>
          <Text style={styles.content}>
            Respuestas Correctas: {this.state.stats.correct_answer0}
          </Text>
          <Text style={styles.content}>
            Respuestas Incorrectas: {this.state.stats.incorrect_answer0}
          </Text>
          <Text style={styles.cat}>
            Cirugia
          </Text>
          <Text style={styles.content}>
            Puntos: {this.state.stats.points1}
          </Text>
          <Text style={styles.content}>
            Respuestas Correctas: {this.state.stats.correct_answer1}
          </Text>
          <Text style={styles.content}>
            Respuestas Incorrectas: {this.state.stats.incorrect_answer1}
          </Text>
          <Text style={styles.cat}>
            Farmacologia
          </Text>
          <Text style={styles.content}>
            Puntos: {this.state.stats.points2}
          </Text>
          <Text style={styles.content}>
            Respuestas Correctas: {this.state.stats.correct_answer2}
          </Text>
          <Text style={styles.content}>
            Respuestas Incorrectas: {this.state.stats.incorrect_answer2}
          </Text>
          <Text style={styles.cat}>
            Grandes Animales
          </Text>
          <Text style={styles.content}>
            Puntos: {this.state.stats.points3}
          </Text>
          <Text style={styles.content}>
            Respuestas Correctas: {this.state.stats.correct_answer3}
          </Text>
          <Text style={styles.content}>
            Respuestas Incorrectas: {this.state.stats.incorrect_answer3}
          </Text>
          <Text style={styles.cat}>
            Pequenos Animales
          </Text>
          <Text style={styles.content}>
          Puntos:  {this.state.stats.points4}
          </Text>
          <Text style={styles.content}>
            Respuestas Correctas: {this.state.stats.correct_answer4}
          </Text>
          <Text style={styles.content}>
            Respuestas Incorrectas: {this.state.stats.incorrect_answer4}
          </Text>
          <Text style={styles.cat}>
            Quimica
          </Text>
          <Text style={styles.content}>
          Puntos:  {this.state.stats.points5}
          </Text>
          <Text style={styles.content}>
            Respuestas Correctas: {this.state.stats.correct_answer5}
          </Text>
          <Text style={styles.content}>
            Respuestas Incorrectas: {this.state.stats.incorrect_answer5}
          </Text>
          <Button
            onPress={() => this.props.navigation.navigate('App')}
            title="<-"
            color="#000000"
          />
        </ScrollView>
    </View>

    );
  }

}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#424949',
  },
  title: {
    fontSize: 30,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#FFFFFF',
  },
  cat: {
    fontSize: 20,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#424949',
  },
  content: {
    fontSize: 15,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#424949',
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

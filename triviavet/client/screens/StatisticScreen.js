import * as Progress from 'react-native-progress'
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
  FlatList,
} from 'react-native';
import axios from 'axios';

export default class QuestionScreen extends React.Component {
  static navigationOptions = {
    title: 'Estadisticas',
    category: AsyncStorage.getItem('category')
  };

  constructor(props) {
    super(props);
    this.state = {
      stats: [],
      perc: []
    }
  }

  async componentDidMount () {
    const token =  await AsyncStorage.getItem('userToken')
    await axios.get(API_HOST+"/logged/statistics",{
      headers:{'Authorization' : token}})
    .then(
      response => JSON.parse(JSON.stringify(response))
    )
    .then(async ({data}) => {
      await this.setState({stats: data.statistics});
      for(i=0;i<this.state.stats.length;i++){
        this.state.perc.push(this.state.stats[i].correct_percentage);
      }
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
      <ScrollView>
      <FlatList
        data={this.state.stats}
        keyExtractor={(x, i) => i.toString()}
        ItemSeparatorComponent={this._renderSeparator}
        renderItem={({item,index}) =>


          <View style={styles.container}>
            <Text style={styles.title}>
              {item.cat}
            </Text>
            <Text style={styles.content}>
              Respuestas correctas: {item.correct_answer}
            </Text>
              <Text style={styles.content}>
              Respuestas incorrectas: {item.incorrect_answer}
            </Text>
            <Text style={styles.content}>
              Respuestas totales: {item.correct_answer+item.incorrect_answer}
            </Text>
            <Text style={styles.content}>
              Porcentaje total:
            </Text>
            <Progress.Circle progress={this.state.perc[index]} size={120}
              color="#3498db"
              borderWidth={2}
              showsText={true}
              unfilledColor="#7fcbfd"
              endAngle={0.9}
            />
          </View>
        }
    />


          <Button  title="Volver" onPress={() => this.props.navigation.navigate('App')}
            color="#ebee2c"
          />
        </ScrollView>
    </View>

    );
  }
  _renderSeparator() {
    return (
      <View style={styles.separator}/>
    )
  }

}
const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    backgroundColor: '#48c9b0',
    alignItems: 'center',
  },
  title: {
    fontSize: 30,
    textAlign: 'center',
    margin: 20,
  },
  cat: {
    fontSize: 20,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#48c9b0',
  },
  content: {
    fontSize: 15,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#48c9b0',
  },
  input: {
    margin: 20,
    height: 40,
    padding: 5,
    fontSize: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#4228F8'
  },
  separator: {
    margin: 10
  }
})

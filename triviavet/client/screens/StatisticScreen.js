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
              <Text style={styles.tabBarInfoTextTitle}>{item.cat}</Text>
            </Text>
            <Text style={styles.content}>
             <Text style={styles.tabBarInfoText}> Respuestas correctas: {item.correct_answer}</Text>
            </Text>
              <Text style={styles.content}>
               <Text style={styles.tabBarInfoText}>Respuestas incorrectas: {item.incorrect_answer}</Text>
            </Text>
            <Text style={styles.content}>
             <Text style={styles.tabBarInfoText}> Respuestas totales: {item.correct_answer+item.incorrect_answer}</Text>
            </Text>
            <Text style={styles.content}>
             <Text style={styles.tabBarInfoText}> Porcentaje total:</Text>
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
            color="#d35400"
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
    backgroundColor: '#1b4f72',
    alignItems: 'center',
  },
  title: {
    fontSize: 30,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#1b4f72',
  },
  cat: {
    fontSize: 20,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#FFFFFF',
  },
  content: {
    fontSize: 15,
    textAlign: 'center',
    margin: 20,
    backgroundColor: '#1b4f72',
  },
  input: {
    margin: 20,
    height: 40,
    padding: 5,
    fontSize: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#FFFFFF'
  },
  tabBarInfoText: {
    fontSize: 17,
    color: '#FFFFFF',
    textAlign: 'center',
   }, 
   tabBarInfoTextTitle: {
    fontSize: 30,
    color: '#FFFFFF',
    textAlign: 'center',
   }, 
  separator: {
    margin: 10
  }
})

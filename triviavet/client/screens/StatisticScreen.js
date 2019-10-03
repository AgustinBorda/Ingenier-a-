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
    title: '',
    category: AsyncStorage.getItem('category')
  };

  constructor(props) {
    super(props);
    this.state = {
      stats: []
    }
  }

  async componentWillMount () {
    const token =  await AsyncStorage.getItem('userToken')
    await axios.get(API_HOST+"/logged/statistics",{
      headers:{'Authorization' : token}})
    .then(
      response => JSON.parse(JSON.stringify(response))
    )
    .then(async ({data}) => {
      await this.setState({stats: data.statistics});
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
      <FlatList
        data={this.state.stats}
        keyExtractor={(x, i) => i.toString()}
        ItemSeparatorComponent={this._renderSeparator}
        renderItem={({item}) =>


          <Text style={styles.title}>
          {item.cat}

          <Progress.Circle progress={item.correct_percentage} size={120}
          color="#3498db"
          borderWidth={2}
          showsText={true}
          unfilledColor="#7fcbfd"
          endAngle={0.9}
          />
          </Text>


        }
    />


          <Button  title="back" onPress={() => this.props.navigation.navigate('App')}
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

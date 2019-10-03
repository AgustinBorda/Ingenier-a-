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
  FlatList,
} from 'react-native';
import axios from 'axios';

export default class PlayScreen extends React.Component {
  static navigationOptions = {
    title: '',
  };

  constructor(props) {
    super(props);
    this.state = {
      categories: []
    }
  }

  fetchData = async () => {
    const token =  await AsyncStorage.getItem('userToken');
    await axios.get(API_HOST+"/logged/category",{
                headers:{'Authorization' : token}
              })
              .then(({data}) => {
                this.setState({ categories: data.categories});
              });
    };

  componentWillMount() {
    this.fetchData();
  }

  render() {
    return (
      <View style={styles.container}>
      <View style={{margin:20}} />
        <Text style={styles.welcome}> Select a Category, or Random </Text>
        <View style={{margin:40}} />
          <FlatList
            data={this.state.categories}
            keyExtractor={(x, i) => i.toString()}
            ItemSeparatorComponent={this._renderSeparator}
            renderItem={({item}) =>

              <Button
                onPress={this.onPressCategoryButton.bind(this, Object.values({item}).toString())}
                title={item}
                color="#3498db"
                style={{margin:40}}
              />

            }
        />
        <Button
          onPress={() => this.props.navigation.navigate('Question')}
          title="Random"
          color="#000000"
        />
        <View style={{margin:20}} />

        <Button
          onPress={() => this.props.navigation.navigate('App')}
          title="Volver"
          color="#ebee2c"
        />
      </View>
    );
  }
  onPressCategoryButton = (category) => {
    AsyncStorage.setItem('category',category);
    this.props.navigation.navigate('Question')
  }

  _renderSeparator() {
    return (
      <View style={styles.separator}/>
    )
  }
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#48c9b0',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  input: {
    margin: 15,
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

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

export default class PlayScreen extends React.Component {
  static navigationOptions = {
    title: '',
  };

  constructor(props) {
    super(props);
    this.state = {
      category: ''
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}> Select a Category, or Random </Text>
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'quimica')}
          title="Quimica"
          color="#8B0000"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'anatomia')}
          title="Anatomia"
          color="#FF8C00"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'cirugia')}
          title="Cirugia"
          color="#9932CC"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'farmacologia')}
          title="Farmacologia"
          color="#008000"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'grandes_animales')}
          title="Grandes Animales"
          color="#4682B4"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'pequenos_animales')}
          title="Pequenos Animales"
          color="#FFC0CB"
        />
        <Button
          onPress={() => this.props.navigation.navigate('Question')}
          title="Random"
          color="#000000"
        />
        <Button
          onPress={() => this.props.navigation.navigate('App')}
          title="atras"
          color="#8B0000"
        />

      </View>
    );
  }
  onPressCategoryButton = (category) => {
    AsyncStorage.setItem('category',category);
    this.props.navigation.navigate('Question')
  }

}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#F5FCFF',
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
  }
})

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
      category: ''
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}> Select a Category, or Random </Text>
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'Categoria 1')}
          title="1"
          color="#8B0000"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'Categoria 2')}
          title="2"
          color="#FF8C00"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'Categoria 3')}
          title="3"
          color="#9932CC"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'Categoria 4')}
          title="4"
          color="#008000"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'Categoria 5')}
          title="5"
          color="#4682B4"
        />
        <Button
          onPress={this.onPressCategoryButton.bind(this, 'Categoria 6')}
          title="6"
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
    alert(category);
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

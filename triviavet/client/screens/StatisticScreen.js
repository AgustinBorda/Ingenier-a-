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
      stats: "",
      progress: 0.6
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
  componentDitMount(){
    this.buscarlos();
  }
  buscarlos(){
    let progress=0.9; 
    this.setState({progress});
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>
          Estadisticas:
        </Text>
      <ScrollView>
     
          <Text style={styles.cat}>
          Anatomia:
          </Text>
          
          <Progress.Pie progress={0.5} size={120} 
           color="#3498db"
           borderWidth={2}
           unfilledColor="#7fcbfd"
          />
        
          
          
          <Text style={styles.cat}>
            Cirugia
          </Text>
          <Progress.Circle progress={this.state.progress} size={120}
           color="#3498db"
           borderWidth={2}
           showsText
           unfilledColor="#7fcbfd"
           endAngle={0.9}
           />
          
          <Text style={styles.cat}>
            Farmacologia
         </Text>
          <Progress.Bar progress={0.7} width={300} 
          height={20}
          color="#3498db"
          unfilledColor="#7fcbfd"
          borderWidth={2}
          />
            
          <Text style={styles.cat}>
            Grandes Animales
          </Text>
             <Progress.Pie progress={0.5} size={120} 
           color="#3498db"
           borderWidth={2}
           unfilledColor="#7fcbfd"


          />
        
              
          <Text style={styles.cat}>
            Pequenos Animales
          </Text>
             <Progress.Pie progress={0.5} size={120} 
           color="#3498db"
           borderWidth={2}
           unfilledColor="#7fcbfd"
          />
        
             

          <Text style={styles.cat}>
            Quimica
          </Text>
             <Progress.Pie progress={0.5} size={120} 
           color="#3498db"
           borderWidth={2}
           unfilledColor="#7fcbfd"
          />
         
           
          <Button  title="back" onPress={() => this.props.navigation.navigate('App')}
            color="#ebee2c"
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
  }
})

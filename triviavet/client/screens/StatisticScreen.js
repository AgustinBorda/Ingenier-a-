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
  componentDidMount(){
    this.buscarlos();
  }
  buscarlos(){
    let progressAnatomia=0.9;
    let progressCirugia=0.7;
    let progressFarmacologia=0.3;
    let progressGrandes_Animales=0.6;
    let progressPequeños_Animales=0.9;
    let progressQuimica=0.1;

    this.setState({progressAnatomia});
    this.setState({progressCirugia});
    this.setState({progressGrandes_Animales});
    this.setState({progressPequeños_Animales});
    this.setState({progressQuimica});
    this.setState({progressFarmacologia});
    
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
          
          <Progress.Circle progress={this.state.progressAnatomia} size={120}
           color="#3498db"
           borderWidth={2}
           showsText
           unfilledColor="#7fcbfd"
           endAngle={0.9}
           />
        
          
          
          <Text style={styles.cat}>
            Cirugia
          </Text>
          <Progress.Circle progress={this.state.progressCirugia} size={120}
           color="#3498db"
           borderWidth={2}
           showsText
           unfilledColor="#7fcbfd"
           endAngle={0.9}
           />
          
          <Text style={styles.cat}>
            Farmacologia
         </Text>
          
          <Progress.Circle progress={this.state.progressFarmacologia} size={120}
           color="#3498db"
           borderWidth={2}
           showsText
           unfilledColor="#7fcbfd"
           endAngle={0.9}
           />
            
          <Text style={styles.cat}>
            Grandes Animales
          </Text>
           <Progress.Circle progress={this.state.progressGrandes_Animales} size={120}
           color="#3498db"
           borderWidth={2}
           showsText
           unfilledColor="#7fcbfd"
           endAngle={0.9}
           />
              
          <Text style={styles.cat}>
            Pequenos Animales
          </Text>
          
          <Progress.Circle progress={this.state.progressPequeños_Animales} size={120}
           color="#3498db"
           borderWidth={2}
           showsText
           unfilledColor="#7fcbfd"
           endAngle={0.9}
           />
        
             

          <Text style={styles.cat}>
            Quimica
          </Text>
          
          <Progress.Circle progress={this.state.progressQuimica} size={120}
           color="#3498db"
           borderWidth={2}
           showsText
           unfilledColor="#7fcbfd"
           endAngle={0.9}
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

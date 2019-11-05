import { createSwitchNavigator, createStackNavigator, createAppContainer } from 'react-navigation';

import HomeScreen from '../screens/HomeScreen';
import SignInScreen from '../screens/SignInScreen';
import AuthLoadingScreen from '../screens/AuthLoadingScreen';
import CreateUserScreen from '../screens/CreateUserScreen';
import PlayScreen from '../screens/PlayScreen';
import QuestionScreen from '../screens/QuestionScreen';
import StatisticScreen from '../screens/StatisticScreen';
import ResetPassScreen from '../screens/ResetPass';
import InsertCodeScreen from '../screens/InsertCode';

const AppStack = createStackNavigator({ Home: HomeScreen});
const AuthStack = createStackNavigator({ SignIn: SignInScreen });
const CreateStack = createStackNavigator({ CreateUser: CreateUserScreen })
const PlayStack = createStackNavigator({ Play: PlayScreen})
const QuestionStack = createStackNavigator({Question: QuestionScreen})
const StatisticStack = createStackNavigator({Statistic: StatisticScreen})
const ResetPassStack = createStackNavigator({ResetPass: ResetPassScreen})
const InsertCodeStack = createStackNavigator({ResetPass: InsertCodeScreen})

export default createAppContainer(createSwitchNavigator(
  {
    AuthLoading: AuthLoadingScreen,
    App: AppStack,
    Auth: AuthStack,
    Create: CreateStack,
    Play: PlayScreen,
    Question: QuestionStack,
    Statistic: StatisticStack,
    ResetPass: ResetPassStack,
    InsertCode: InsertCodeStack
  },
  {
    initialRouteName: 'AuthLoading',
  }
));

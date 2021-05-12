import {
  NativeModules,
  Platform,
} from 'react-native';

export default Platform.select({
  ios: null,
  android: NativeModules.Scanner,
});
import { Moment } from 'moment';
import { IPoruka } from 'app/shared/model/poruka.model';
import { IChat } from 'app/shared/model/chat.model';

export interface IGrupacijaPoruka {
  id?: number;
  datum?: string;
  porukas?: IPoruka[];
  chat?: IChat;
}

export const defaultValue: Readonly<IGrupacijaPoruka> = {};

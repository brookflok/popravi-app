import { Moment } from 'moment';
import { IChat } from 'app/shared/model/chat.model';
import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';

export interface IUcesnici {
  id?: number;
  datum?: string;
  chat?: IChat;
  dodatniInfoUser?: IDodatniInfoUser;
}

export const defaultValue: Readonly<IUcesnici> = {};

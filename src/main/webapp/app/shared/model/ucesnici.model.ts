import { Moment } from 'moment';
import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { IChat } from 'app/shared/model/chat.model';

export interface IUcesnici {
  id?: number;
  datum?: string;
  dodatniInfoUsers?: IDodatniInfoUser[];
  chat?: IChat;
}

export const defaultValue: Readonly<IUcesnici> = {};

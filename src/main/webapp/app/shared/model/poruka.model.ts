import { Moment } from 'moment';
import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { IGrupacijaPoruka } from 'app/shared/model/grupacija-poruka.model';

export interface IPoruka {
  id?: number;
  text?: string;
  datum?: string;
  postoji?: boolean;
  dodatniinfouser?: IDodatniInfoUser;
  grupacijaPoruka?: IGrupacijaPoruka;
}

export const defaultValue: Readonly<IPoruka> = {
  postoji: false,
};

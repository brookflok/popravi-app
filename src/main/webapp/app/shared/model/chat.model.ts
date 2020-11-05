import { Moment } from 'moment';
import { IUcesnici } from 'app/shared/model/ucesnici.model';
import { IGrupacijaPoruka } from 'app/shared/model/grupacija-poruka.model';
import { IArtikl } from 'app/shared/model/artikl.model';

export interface IChat {
  id?: number;
  datum?: string;
  postoji?: boolean;
  ucesnici?: IUcesnici;
  grupacijaPoruka?: IGrupacijaPoruka;
  artikl?: IArtikl;
}

export const defaultValue: Readonly<IChat> = {
  postoji: false,
};

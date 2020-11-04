import { Moment } from 'moment';
import { IArtikl } from 'app/shared/model/artikl.model';

export interface IMainSlika {
  id?: number;
  ime?: string;
  slikaContentType?: string;
  slika?: any;
  datum?: string;
  artikl?: IArtikl;
}

export const defaultValue: Readonly<IMainSlika> = {};

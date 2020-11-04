import { Moment } from 'moment';
import { IArtikl } from 'app/shared/model/artikl.model';

export interface IMainSlika {
  id?: number;
  title?: string;
  created?: string;
  artikl?: IArtikl;
}

export const defaultValue: Readonly<IMainSlika> = {};

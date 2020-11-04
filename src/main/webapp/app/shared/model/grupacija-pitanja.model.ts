import { Moment } from 'moment';
import { IArtikl } from 'app/shared/model/artikl.model';

export interface IGrupacijaPitanja {
  id?: number;
  datum?: string;
  artikl?: IArtikl;
}

export const defaultValue: Readonly<IGrupacijaPitanja> = {};

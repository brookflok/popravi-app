import { Moment } from 'moment';
import { IJavnoPitanje } from 'app/shared/model/javno-pitanje.model';
import { IArtikl } from 'app/shared/model/artikl.model';

export interface IGrupacijaPitanja {
  id?: number;
  datum?: string;
  javnoPitanjes?: IJavnoPitanje[];
  artikl?: IArtikl;
}

export const defaultValue: Readonly<IGrupacijaPitanja> = {};

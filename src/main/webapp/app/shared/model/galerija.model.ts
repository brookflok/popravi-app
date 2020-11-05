import { Moment } from 'moment';
import { ISlika } from 'app/shared/model/slika.model';
import { IArtikl } from 'app/shared/model/artikl.model';

export interface IGalerija {
  id?: number;
  ime?: string;
  datum?: string;
  slikas?: ISlika[];
  artikl?: IArtikl;
}

export const defaultValue: Readonly<IGalerija> = {};

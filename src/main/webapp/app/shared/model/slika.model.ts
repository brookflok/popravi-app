import { Moment } from 'moment';
import { IGalerija } from 'app/shared/model/galerija.model';

export interface ISlika {
  id?: number;
  ime?: string;
  slikaContentType?: string;
  slika?: any;
  uploaded?: string;
  galerija?: IGalerija;
}

export const defaultValue: Readonly<ISlika> = {};

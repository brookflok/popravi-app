import { Moment } from 'moment';
import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';

export interface IProfilnaSlika {
  id?: number;
  ime?: string;
  slikaContentType?: string;
  slika?: any;
  datum?: string;
  dodatniInfoUser?: IDodatniInfoUser;
}

export const defaultValue: Readonly<IProfilnaSlika> = {};

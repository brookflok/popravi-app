import { IArtikl } from 'app/shared/model/artikl.model';

export interface IKategorija {
  id?: number;
  name?: string;
  artikls?: IArtikl[];
}

export const defaultValue: Readonly<IKategorija> = {};

import { Moment } from 'moment';
import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { IJavnoPitanje } from 'app/shared/model/javno-pitanje.model';

export interface IOdgovorNaJavnoPitanje {
  id?: number;
  odgovor?: string;
  datum?: string;
  prikaz?: boolean;
  dodatniinfoUser?: IDodatniInfoUser;
  javnoPitanje?: IJavnoPitanje;
}

export const defaultValue: Readonly<IOdgovorNaJavnoPitanje> = {
  prikaz: false,
};

import { Moment } from 'moment';
import { IOdgovorNaJavnoPitanje } from 'app/shared/model/odgovor-na-javno-pitanje.model';
import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { IGrupacijaPitanja } from 'app/shared/model/grupacija-pitanja.model';

export interface IJavnoPitanje {
  id?: number;
  pitanje?: string;
  datum?: string;
  prikaz?: boolean;
  odgovorNaJavnoPitanje?: IOdgovorNaJavnoPitanje;
  dodatniinfoUser?: IDodatniInfoUser;
  grupacijapitanja?: IGrupacijaPitanja;
}

export const defaultValue: Readonly<IJavnoPitanje> = {
  prikaz: false,
};

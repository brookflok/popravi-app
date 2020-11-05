import { ILokacija } from 'app/shared/model/lokacija.model';
import { IPotreba } from 'app/shared/model/potreba.model';
import { IUsluga } from 'app/shared/model/usluga.model';
import { IGalerija } from 'app/shared/model/galerija.model';
import { IMainSlika } from 'app/shared/model/main-slika.model';
import { IInformacije } from 'app/shared/model/informacije.model';
import { IGrupacijaPitanja } from 'app/shared/model/grupacija-pitanja.model';
import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';

export interface IArtikl {
  id?: number;
  ime?: string;
  kratkiOpis?: string;
  detaljniOpis?: string;
  majstor?: boolean;
  postoji?: boolean;
  lokacija?: ILokacija;
  potreba?: IPotreba;
  usluga?: IUsluga;
  galerija?: IGalerija;
  mainSlika?: IMainSlika;
  informacije?: IInformacije;
  grupacijaPitanja?: IGrupacijaPitanja;
  dodatniinfouser?: IDodatniInfoUser;
}

export const defaultValue: Readonly<IArtikl> = {
  majstor: false,
  postoji: false,
};

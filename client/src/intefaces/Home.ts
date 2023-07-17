export interface recommendDataType {
  storeId: number;
  storeName: string;
  body: string;
  img: string;
}

export interface homeMapDataType {
  storeId: number;
  storeName: string;
  latitude: number;
  longitude: number;
  category: string;
}

export interface homeDataType {
  recommend: recommendDataType[];
  data: homeMapDataType[];
}

export interface homeMapPropsType {
  marker: homeMapDataType[];
}

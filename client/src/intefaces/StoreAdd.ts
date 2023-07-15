export interface StoreAddFormType {
  storeName: string;
  body: string;
  address: string;
  kakao: string;
  contact: string;
  category: string;
  items: StoreItemsType[];
}

export interface StoreItemsType {
  itemName: string;
  price: number;
  totalTicket: number;
}
export interface Rental {
	id: number,
	name: string,
	surface: number,
	price: number,
	pictures: string[],
	description: string,
	categorie_id: number,
	owner_id: number,
	created_at: Date,
	updated_at: Date
}
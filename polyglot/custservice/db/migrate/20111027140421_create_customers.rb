class CreateCustomers < ActiveRecord::Migration
  def self.up
    create_table :customers do |t|
      t.string :customer_id
      t.string :first_name
      t.string :last_name
      t.string :street_address
      t.string :city
      t.string :state
      t.string :zip_code
      t.string :creditcard_no
      t.integer :credit_score

      t.timestamps
    end
  end

  def self.down
    drop_table :customers
  end
end

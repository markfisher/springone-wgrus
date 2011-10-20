ENV["BUNDLE_GEMFILE"] ||= File.expand_path("./Gemfile", __FILE__)
require 'rubygems'  
require 'sinatra'
require 'thin'
require 'yajl'
require 'logger'
require 'mysql2'

# VCAP environment
port = ENV['VMC_APP_PORT']
port ||= 8082

class Billing < Sinatra::Base

  def initialize(opts)
    super
    @dbopts = opts
    @logger = Logger.new(STDOUT, 'daily')
    @logger.level = Logger::DEBUG
  end

  get '/authorize/:id' do  
    db = Mysql2::Client.new(@dbopts)
    @logger.info("AUTH: #{params[:id]}")
    results = db.query("SELECT creditcard_no, credit_score FROM customers where id = #{params[:id]}")
    @logger.info("ROW: #{results.first}")
    if results.first == nil
      "FAIL:NOT_FOUND"
    elsif results.first['credit_score'] == nil
      "FAIL:NO_CREDIT"
    elsif results.first['credit_score'] < 650
      "FAIL:BAD_CREDIT"
    else
      "OK:" + params[:id]
    end    
  end
  
end

dbconfig = {}
svcs = ENV['VMC_SERVICES']
if svcs
  svcs = Yajl::Parser.parse(svcs)
  svcs.each do |svc|
    if svc["name"] =~ /^mysql/
      opts = svc["options"]
      user,passwd,host,db,db_port = %w(user password hostname name port).map {|key|
        opts[key]}
      dbconfig[:username] = user
      dbconfig[:password] = passwd
      dbconfig[:host] = host
      dbconfig[:port] = db_port
      dbconfig[:database] = db
    end
  end
end

puts "Config: #{dbconfig.inspect}"
instance = Billing.new(dbconfig)
Thin::Server.start('0.0.0.0', port , instance)

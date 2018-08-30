package com.impalapay.airtel.servlet.export.excel;

public class tester {
	
	public String testing(){
		String username ="9756f889-811a-4a94-b13d-1c66c7655a7f";
		
		StringBuffer query = new StringBuffer("SELECT transaction.uuid,transaction.sourceCountrycode,transaction.senderName,transaction.recipientMobile, ")
		.append("transaction.senderToken,transaction.currencyCode,transaction.recipientcountryUuid,transaction.referenceNumber,")
		.append("transaction.transactionStatusUuid,transaction.clientTime ")
		.append("FROM transaction ")
		.append("INNER JOIN country ON recipientcountryUuid=country.uuid ")
		.append("INNER JOIN transactionStatus ON transaction.transactionStatusUuid=transactionStatus.uuid ")
		.append("WHERE accountUuid = '")
		.append(username)
		.append("';");
		
		return query.toString();
	}
    
	
	public static void main(String[] args){
		tester veve = new tester();
		
		
		
		System.out.println(veve.testing());
	}
}

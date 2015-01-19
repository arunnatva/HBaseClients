import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class MyHBaseClient {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("hi arun, welcome to hbase client");
		Configuration config = HBaseConfiguration.create();
			config.set("hbase.zookeeper.quorum","zkhost1,zkhost2,zkhost3");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		//config.set("zookeeper.znode.parent", "/hbase-unsecure");
		config.set("zookeeper.znode.parent", "/hbase");
		HTable testTable = new HTable(config, "Account_SmokeTest");
		
	    //for (int i = 0; i < 100; i++) {
			byte[] AcctFamily = Bytes.toBytes("Account");
			byte [] CustFamily = Bytes.toBytes("Customer");
			byte [] TypeCol = Bytes.toBytes("Type");
			byte [] balCol = Bytes.toBytes("bal");
			byte [] nbrCol = Bytes.toBytes("nbr");
			byte [] addrCol = Bytes.toBytes("addr");
			byte [] fNameCol = Bytes.toBytes("fName");
			byte [] lNameCol = Bytes.toBytes("lName");
		    
			//System.out.println("test display :" + AcctFamily.toString());
			
			Scan scan = new Scan();
			scan.addColumn(AcctFamily,TypeCol);
			scan.addColumn(AcctFamily,balCol);
			scan.addColumn(AcctFamily,nbrCol);
			scan.addColumn(CustFamily,addrCol);
			scan.addColumn(CustFamily,fNameCol);
			scan.addColumn(CustFamily,lNameCol);
			ResultScanner rs = testTable.getScanner(scan);
			
			StringBuilder sb = new StringBuilder();
			for (Result r = rs.next(); r != null; r = rs.next()) {
				sb.append(new String(r.getValue(AcctFamily, TypeCol)));
				sb.append(" ");
			    sb.append(new String(r.getValue(AcctFamily, balCol)));
			    sb.append(" ");
			    sb.append(new String(r.getValue(AcctFamily,nbrCol)));
			    sb.append(" ");
			    sb.append(new String(r.getValue(CustFamily,addrCol)));
			    sb.append(" ");
			    sb.append(new String(r.getValue(CustFamily, fNameCol)));
			    sb.append(" ");
			    sb.append(new String(r.getValue(CustFamily,lNameCol)));
			    
			    System.out.println("the record is : " + sb.toString());
			}
			
			// to be continued
	//	}  
	testTable.close();
	}

}

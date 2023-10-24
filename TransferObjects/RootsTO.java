package TransferObjects;

public class RootsTO {
	private String root;
	private String new_Root;
	RootsTO(String root, String new_Root)
	{
		this.root=root;
		this.new_Root=new_Root;
		
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getNew_Root() {
		return new_Root;
	}
	public void setNew_root(String new_Root) {
		this.new_Root = new_Root;
	}
	//making TO of roots

}


base_dir=$(abspath .)

export MILL_LIB=$(base_dir)/.chisel/jars
export COURSIER_CACHE=$(base_dir)/.chisel/cache

MILL_VER=0.10.9
MILL_URL=https://github.com/lihaoyi/mill/releases/download/$(MILL_VER)/$(MILL_VER)-assembly
MILL_BIN=$(base_dir)/.chisel/mill

PACK_NAME=chisel

$(MILL_BIN):
	mkdir -p .chisel
	curl --fail -L -o $(MILL_BIN) $(MILL_URL)
	chmod +x $(MILL_BIN)

verilog: $(MILL_BIN)
	$(MILL_BIN) chisel.run -td $(base_dir)/builds

pack: verilog
	tar czf $(PACK_NAME).tar.gz .chisel

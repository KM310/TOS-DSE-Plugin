{
  description = "Minecraft Plugin Build Environment (Kotlin + Gradle + Paper API)";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs { inherit system; };
    in {
      devShell.${system} = pkgs.mkShell {
        buildInputs = [
          pkgs.openjdk21
          pkgs.gradle
          pkgs.git
          pkgs.bash
        ];

        shellHook = ''
          echo "=== Nix DevShell ready ==="
          echo "Java: $(java -version 2>&1 | head -n1)"
          echo "Gradle: $(gradle --version | head -n2 | tail -n1)"
          echo "=========================="
        '';
      };
    };
}

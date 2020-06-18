#include <iostream>
#include "clang/AST/AST.h"
#include "clang/AST/ASTConsumer.h"
#include "clang/ASTMatchers/ASTMatchers.h"
#include "clang/ASTMatchers/ASTMatchFinder.h"
#include "clang/Frontend/CompilerInstance.h"
#include "clang/Frontend/FrontendPluginRegistry.h"

using namespace clang;
using namespace std;
using namespace llvm;
using namespace clang::ast_matchers;

namespace MJPlugin {
    class MJHandler : public MatchFinder::MatchCallback {
    private:
        CompilerInstance &ci;
        
    public:
        MJHandler(CompilerInstance &ci) :ci(ci) {}
        
        void run(const MatchFinder::MatchResult &Result) {
            if (const ObjCInterfaceDecl *decl = Result.Nodes.getNodeAs<ObjCInterfaceDecl>("ObjCInterfaceDecl")) {
                size_t pos = decl->getName().find('_');
                if (pos != StringRef::npos) {
                    DiagnosticsEngine &D = ci.getDiagnostics();
                    SourceLocation loc = decl->getLocation().getLocWithOffset(pos);
                    D.Report(loc, D.getCustomDiagID(DiagnosticsEngine::Error, "M了个J：类名中不能带有下划线"));
                }
            }
        }
    };
    
    class MJASTConsumer: public ASTConsumer {
    private:
        MatchFinder matcher;
        MJHandler handler;
        
    public:
        MJASTConsumer(CompilerInstance &ci) :handler(ci) {
            matcher.addMatcher(objcInterfaceDecl().bind("ObjCInterfaceDecl"), &handler);
        }
        
        void HandleTranslationUnit(ASTContext &context) {
            matcher.matchAST(context);
        }
    };

    class MJASTAction: public PluginASTAction {
    public:
        unique_ptr<ASTConsumer> CreateASTConsumer(CompilerInstance &ci, StringRef iFile) {
            return unique_ptr<MJASTConsumer> (new MJASTConsumer(ci));
        }

        bool ParseArgs(const CompilerInstance &ci, const vector<string> &args) {
            return true;
        }
    };
}

static FrontendPluginRegistry::Add<MJPlugin::MJASTAction>
X("MJPlugin", "The MJPlugin is my first clang-plugin.");

